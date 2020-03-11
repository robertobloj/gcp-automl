# SQL 

## Create mysql instance

1. [Create instance]:

```
gcloud sql instances create INSTANCE --region="us-central" --database-version=MYSQL_5_7 --root-password PASSWORD --require-ssl
```

2. Because we enabled ssl with previous step, we need to [create client certificate] for database instance

```
gcloud sql ssl client-certs create COMMON_NAME CERT_FILE --instance INSTANCE
``` 

3. Download certificate:

Before we download, check whether we have certificate:

```
gcloud sql ssl client-certs list --instance INSTANCE
```

There is no direct download command, so we need to do it in [following way]:

```
gcloud sql ssl client-certs describe COMMON_NAME --instance INSTANCE --flatten cert | sed -- 's/  //g' | sed -- 's/^-^-^- ^|^-//g' > CERT_FILE
```

## Create database and user:

1. [Create database]:

```
gcloud sql databases create DATABASE --instance INSTANCE
```

2. [Create user]:

```
gcloud sql users create USER --instance INSTANCE --password PASSWORD --host %
```

## Connect to database

[Connect to database]

```
gcloud sql connect INSTANCE --database DATABASE --user USER
```



#####

5. 

```

```


Y. [Delete certificate]:

```
gcloud -q sql ssl client-certs delete COMMON_NAME --instance INSTANCE
```

Z. [Delete instance]: 

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
[Connect to database]: https://cloud.google.com/sdk/gcloud/reference/sql/connect
