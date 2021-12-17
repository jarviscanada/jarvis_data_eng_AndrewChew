#! /bin/bash

# Check # args.
if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  echo "host_usage requires: psql_host, psql_port, db_name, psql_user, psql_password"
  exit 1
fi

# Setup arguments.
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Save host name to variable.
hostname=$(hostname -f)

# Save machine statistics to variables.
vmstat_t=$(vmstat -t)
vmstat_m=$(vmstat --unit M)
vmstat_d=$(vmstat -d)
df_bm=$(df -BM /)

# Retrieve current time in `2019-11-26 14:40:19` UTC format.
timestamp=$(echo "$vmstat_t" | tail -1 | awk '{print $18 " "  $19}' | xargs)

# Retrieve usage statistics variables.
memory_free=$(echo "$vmstat_m" | tail -1 | awk '{print $4}' | xargs)
cpu_idle=$(echo "$vmstat_m" | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_m" | tail -1 | awk '{print $14}' | xargs)
disk_io=$(echo "$vmstat_d" | tail -1 | awk '{print $10}' | xargs)
disk_available=$(echo "$df_bm" | tail -1 | awk '{print $4}' | sed 's/M//' | xargs)

# Subquery to find machine id using the machine's host name in the host_info table.
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

# PSQL command: Inserts server usage data into host_usage table.
insert_stmt="INSERT INTO host_usage \
  VALUES('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available)"

# Set up PGPASSWORD environment variable to connect to psql instance without prompting for password.
export PGPASSWORD=$psql_password

#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?