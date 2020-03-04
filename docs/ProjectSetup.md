# Project setup

## Prerequisites

You have to be logged into GCP.

## Create project

[Create new project] and set is as current project: 

```
gcloud projects create PROJECT_ID [--organization ORG_NAME]
gcloud config set project PROJECT_ID
```

Check project details:

```
gcloud projects describe PROJECT_ID
```

## Create Service Account

[Create service account]: 

```
gcloud iam service-accounts create SERVICE_ACCOUNT --display-name="ServiceAccount"
```

Notice that `SERVICE_ACCOUNT` is not an ID for account. ID is built based on `SERVICE_ACCOUNT` and `PROJECT_ID`, 
for example:

```
gcloud projects create EXAMPLE_PROJECT
gcloud iam service-accounts create EXAMPLE_ACCOUNT 

# account id is:
set SERVICE_ACCOUNT_ID=EXAMPLE_ACCOUNT@EXAMPLE_PROJECT.iam.gserviceaccount.com
```

## Create User key

Before we [create key], lets check what we have:

```
gcloud iam service-accounts keys list --iam-account=SERVICE_ACCOUNT_ID

# output
KEY_ID                                    CREATED_AT            EXPIRES_AT
SOME_KEY_ID_f7dacee50f5607beb0020d782e21  2020-03-03T10:36:03Z  2022-03-27T23:59:24Z
```

Lets create new key for `service account`:

```
gcloud iam service-accounts keys create OUTPUT_JSON_FILE --iam-account=SERVICE_ACCOUNT_ID
```

Notice this key should remain private, so do not push it to git, etc. 

## Connect Service Account and Billing Account

Check your [billing accounts]:

```
gcloud beta billing accounts list

# Example output
ACCOUNT_ID            NAME                OPEN  MASTER_ACCOUNT_ID
SOMEID-B3F4EE-AA567B  My Billing Account  True
```

[Connect project with your billing account]:

```
gcloud beta billing projects link PROJECT_ID  --billing-account=ACCOUNT_ID
```

[Create new project]: https://cloud.google.com/resource-manager/docs/creating-managing-projects 
[Create service account]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/create
[create key]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/keys/create
[billing accounts]: (https://cloud.google.com/sdk/gcloud/reference/beta/billing/accounts)
[Connect project with your billing account]: (https://cloud.google.com/sdk/gcloud/reference/beta/billing/projects/link):
