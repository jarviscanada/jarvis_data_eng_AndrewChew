#! /bin/sh

# Capture CLI arguments.
cmd=$1
db_username=$2
db_password=$3

# Check if docker is running, start it otherwise.
sudo systemctl status docker || sudo systemctl start docker

# Check container status.
docker container inspect lil-postgres
container_status=$?

# Case statement to handle create, start, and stop operations.
case $cmd in 
  create)
  
  # Exit if container has already been created.
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 0	
	fi

  # Check # of CLI arguments.
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi
  
  # Create container if it doesn't exist.
	docker run \
	  --name lil-postgres \
	  -e POSTGRES_USER=$db_username \
	  -e POSTGRES_PASSWORD=$db_password \
	  -d \
	  -v $HOME/src/postgres:/var/lib/postgresql/data \
	  -p 5432:5432 \
	  postgres:9.6-alpine
	exit $?
	;;

  start|stop)

  # Check container status; exit 1 if container has not been created.
  if [ $container_status -ne 0 ]; then
    echo 'Container has not been created'
    exit 1
  fi

  # Start or stop the container
	docker container $cmd lil-postgres
	exit $?
	;;
  
  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac
