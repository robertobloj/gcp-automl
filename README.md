# GoogleCloudPlatform AutoML

[AutoML Vision] enables training machine learning models to classify images. 

## Prerequisites

You need to have [GCP SDK](https://cloud.google.com/sdk) installed.

## Setup

### Login into GCP

1. Log into your GCP:

```console
gcloud auth login
```

### Create and setup project

2. [Create new project] and set is as current project: 

```console
gcloud projects create PROJECT_ID [--organization ORG_NAME]
gcloud config set project PROJECT_ID
```

Check project details:

```console
gcloud projects describe PROJECT_ID
```

3. [Create service account]: 

https://cloud.google.com/vision/automl/docs/quickstart

```console
gcloud iam service-accounts create SERVICE_ACCOUNT --display-name="ServiceAccount"
```

Notice that `SERVICE_ACCOUNT` is not an ID for account. ID is built based on `SERVICE_ACCOUNT` and `PROJECT_ID`, 
for example:

```console
gcloud projects create EXAMPLE_PROJECT
gcloud iam service-accounts create EXAMPLE_ACCOUNT 

# account id is:
set SERVICE_ACCOUNT_ID=EXAMPLE_ACCOUNT@EXAMPLE_PROJECT.iam.gserviceaccount.com
```

4. [Add permissions]:

```console
gcloud projects add-iam-policy-binding PROJECT_ID --member="serviceAccount:SERVICE_ACCOUNT_ID" --role "roles/automl.editor"
```

5. [Create key for user]:

Before we create key, lets check what we have:

```console
gcloud iam service-accounts keys list --iam-account=SERVICE_ACCOUNT_ID

# output
KEY_ID                                    CREATED_AT            EXPIRES_AT
ac0a971a44f7f7dacee50f5607beb0020d782e21  2020-03-03T10:36:03Z  2022-03-27T23:59:24Z
```

Lets create new key for `service account`:

```console
gcloud iam service-accounts keys create OUTPUT_FILE --iam-account=SERVICE_ACCOUNT_ID
```

Notice this key should remain private, so do not push it to git, etc. 

### Create storage for images to train


[Read more](https://cloud.google.com/sdk/gcloud/reference) about `gcloud SDK`.

[AutoML Vision]: https://cloud.google.com/automl
[Create new project]: https://cloud.google.com/resource-manager/docs/creating-managing-projects 
[Create service account]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/create
[Add permissions]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/add-iam-policy-binding
[Create key for user]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/keys/create
