# SQL 

## Create mysql instance

1. [Create instance]:

```
gcloud sql instances create INSTANCE --region="us-central" \ 
    --database-version=MYSQL_5_7 --root-password PASSWORD --require-ssl
```

2. Because we enabled ssl with previous step, we need to [create client certificate] for database instance

```
gcloud sql ssl client-certs create COMMON_NAME CERT_FILE --instance INSTANCE
``` 

Notice this command downloads private key into `CERT_FILE`.

3. Optionally download certificate:

Before we download, check whether we have certificate:

```
gcloud sql ssl client-certs list --instance INSTANCE
```

There is no direct download command, so we need to do it in [following way]:

```
gcloud sql ssl client-certs describe COMMON_NAME --instance INSTANCE --flatten cert | \ 
    sed -- 's/  //g' | sed -- 's/^-^-^- ^|^-//g' > CERT_FILE
```

## OPTIONAL STEP - Create database and user:

1. [Create database]:

```
gcloud sql databases create DATABASE --instance INSTANCE
```

2. [Create user]:

```
gcloud sql users create USER --instance INSTANCE --password PASSWORD --host %
```

## Connect to database

1. Before connect we need to [install mysql] client and add it to `PATH`

2. Download [Cloud SQL Proxy] and start:

`Cloud SQL Proxy` allows you to connect to database securely.
 
```
./cloud_sql_proxy -instances=PROJECT_ID:ZONE:INSTANCE=tcp:3306
```

3. Open another terminal and connect to database:

```
mysql -u root -p --host 127.0.0.1 --port 3306
```

4. Do what you need to test

5. Notice you can also connect via `gcloud` command, but it does not support secure connection:

```
gcloud sql connect INSTANCE --database DATABASE --user USER
```

## Delete database

1. [Delete certificate]:

```
gcloud -q sql ssl client-certs delete COMMON_NAME --instance INSTANCE
```

2. [Delete instance]: 

```
gcloud -q sql instances delete INSTANCE
```

[Create instance]: https://cloud.google.com/sdk/gcloud/reference/sql/instances/create
[create client certificate]: https://cloud.google.com/sdk/gcloud/reference/sql/ssl/client-certs/create
[Delete certificate]: https://cloud.google.com/sdk/gcloud/reference/sql/ssl/client-certs/delete
[Delete instance]: https://cloud.google.com/sdk/gcloud/reference/sql/instances/delete
[Create database]: https://cloud.google.com/sdk/gcloud/reference/sql/databases/create
[Create user]: https://cloud.google.com/sdk/gcloud/reference/sql/users/create
[following way]: https://cloud.google.com/sdk/gcloud/reference/sql/ssl/client-certs/describe
[install mysql]: https://dev.mysql.com/downloads/mysql/
[Cloud SQL Proxy]: https://cloud.google.com/sql/docs/mysql/connect-admin-proxy