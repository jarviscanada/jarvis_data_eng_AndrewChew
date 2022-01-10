#! /bin/bash

# Check # args.
if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  echo "host_info requires: psql_host, psql_port, db_name, psql_user, psql_password"
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
lscpu_out=$(lscpu)
vmstat_t=$(vmstat -t)

# Retrieve hardware specification variables.
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model name:" | awk '{for (i=3; i<=NF; i++) print $i}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | sed 's/K//' | xargs)
total_mem=$(head -1 /proc/meminfo | awk '{print $2}' | xargs)

# Retrieve current time in `2019-11-26 14:40:19` UTC format.
timestamp=$(echo "$vmstat_t" | tail -1 | awk '{print $18 " "  $19}' | xargs)

# PSQL command: Inserts server usage data into host_info table.
insert_stmt="INSERT INTO host_info \
  VALUES(DEFAULT, '$hostname', $cpu_number, '$cpu_architecture',\
        '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp')"

# Set up PGPASSWORD environment variable to connect to psql instance without prompting for password.
export PGPASSWORD=$psql_password

# Insert data record into the database.
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?