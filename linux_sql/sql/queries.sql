-- Function to round current timestamp to a 5-min interval.
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: INT / 5 * INTERVAL '5 min';
END;
$$
    LANGUAGE PLPGSQL;

-- Query 1: Group hosts by hardware info.
-- Group host by CPU number and sort by their memory size in descending order (within each group).
SELECT
    cpu_number,
    id AS host_id,
    MIN(total_mem) OVER
     (PARTITION BY cpu_number ORDER BY total_mem DESC) AS total_mem
FROM
    host_info;

-- Query 2: Average Memory Usage.
-- Average used memory in percentage over 5-min interval for each host.
-- Note: total_mem is given in kB while memory_free is given in MB.
SELECT
    u.host_id,
    i.hostname AS host_name,
    round5(u.timestamp) AS "timestamp",
    ROUND(AVG((total_mem - (memory_free * 1000.0)) / total_mem * 100), 0) AS avg_used_mem_percentage
FROM
    host_usage u JOIN host_info i ON u.host_id = i.id
GROUP BY u.host_id, i.hostname, round5(u.timestamp)
ORDER BY round5(u.timestamp);

-- Query 3: Detect Host Failure.
-- Detect server failures which is defined to be when < 3 data points are inserted within a 5-min interval by cron job.
-- Note: this query makes use of the function 'round5' from the previous query.
SELECT
    host_id,
    round5(timestamp) AS ts,
    COUNT(timestamp) AS num_data_points
FROM
    host_usage
GROUP BY host_id, round5(timestamp)
HAVING COUNT(timestamp) < 3
ORDER BY round5(timestamp);
