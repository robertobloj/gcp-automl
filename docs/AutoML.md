# GoogleCloudPlatform AutoML

[AutoML Vision] enables training machine learning models to classify images. 

## Setup

1. [Create project and service account](ProjectSetup.md) if needed.

2. [Add permissions] to service account:

```
gcloud projects add-iam-policy-binding PROJECT_ID --member="serviceAccount:SERVICE_ACCOUNT_ID" \ 
    --role "roles/automl.editor"
```

## Create storage for images to train

1. Create google storage bucket (at least for now it must be `us-central1`)

```
gsutil mb -p PROJECT_ID -c regional -l us-central1 gs://PROJECT_ID-vcm/
```

2. Copy dataset into storage bucket:

In this step we copy example dataset from google storage bucket, but we can prepare own dataset as well:

```
gsutil -m cp -R gs://cloud-ml-data/img/flower_photos/ gs://PROJECT_ID-vcm/img/
``` 

3. Prepare csv file for training process:

```
gsutil cat gs://PROJECT_ID-vcm/img/flower_photos/all_data.csv | sed "s:cloud-ml-data:PROJECT_ID-vcm:" \ 
    > all_data.csv
gsutil cp all_data.csv gs://PROJECT_ID-vcm/csv/
```

Proper format is as follows:

```csv
gs://PROJECT_ID-vcm/img/flower_photos/daisy/100080576_f52e8ee070_n.jpg,daisy
gs://PROJECT_ID-vcm/img/flower_photos/daisy/10140303196_b88d3d6cec.jpg,daisy
gs://PROJECT_ID-vcm/img/flower_photos/daisy/10172379554_b296050f82_n.jpg,daisy
...
```

## Setup project for AutoML

1. [Enable api] for  `AutoML`:

```
gcloud services enable "automl.googleapis.com"
```

2. [Create dataset] for `AutoML`
3. [Train model] (for practice purpose set `maximum node hours` to minimum value - thanks to that we avoid high bill)

## Test AutoML

1. [Deploy model]
2. Configure credentials:

To run prediction you need to configure google credentials: 

```
set GOOGLE_APPLICATION_CREDENTIALS=SOME_PATH/OUTPUT_JSON_FILE
```

This is required step to run prediction. 

3. Make a prediction

Run `AutoMLApp` application:

![Edit configuration](docs/img/automl-runConfiguration.png)


4. [Undeploy model]
5. Remove Google Storage Bucket

```
gsutil rm -r gs://PROJECT_ID-vcm/
``` 


[AutoML Vision]: https://cloud.google.com/automl
[Add permissions]: https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/add-iam-policy-binding
[Enable api]: https://cloud.google.com/endpoints/docs/openapi/enable-api
[Create dataset]: https://cloud.google.com/vision/automl/docs/quickstart#create_your_dataset
[Train model]: https://cloud.google.com/vision/automl/docs/quickstart#train_your_model
[Deploy model]: https://cloud.google.com/vision/automl/docs/quickstart#manually-deploy-model
[Undeploy model]: https://cloud.google.com/vision/automl/docs/quickstart#undeploy-your-model
