# GoogleCloudPlatform AutoML

[AutoML Vision] enables training machine learning models to classify images. 

## Prerequisites

You need to have [GCP SDK](https://cloud.google.com/sdk) installed.

## Setup

1. Log into your GCP:

```console
gcloud auth login
```

2. [Create new project] and set is as current project: 

```console
gcloud projects create PROJECT_ID [--organization ORG_NAME]
gcloud config set project PROJECT_ID
```

Check project details:

```console
gcloud projects describe PROJECT_ID
```

2. [Create service account]: 

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
EXAMPLE_ACCOUNT@EXAMPLE_PROJECT.iam.gserviceaccount.com
```

3. [Add permissions]:

```console
gcloud projects add-iam-policy-binding PROJECT_ID --member="serviceAccount:SERVICE_ACCOUNT_ID" --role "roles/automl.editor"
```

4. 



[Read more](https://cloud.google.com/sdk/gcloud/reference) about `gcloud SDK`.

[AutoML Vision]: https://cloud.google.com/automl
[Create new project]: https://cloud.google.com/resource-manager/docs/creating-managing-projects 
[Create service account]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/create
[Add permissions]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/add-iam-policy-binding
