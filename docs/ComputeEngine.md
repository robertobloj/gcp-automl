# Compute Engine

## Create VM:

[Create VM](https://cloud.google.com/sdk/gcloud/reference/compute/instances/create) by run 
following command: 

```
gcloud compute instances create INSTANCE --machine-type MACHINE_TYPE \ 
    --project PROJECT_NAME --zone us-central1-a --metadata=enable-oslogin=TRUE
```

Check [machine types], for test it's enough to choose `f1-micro`.
Notice that by `--metadata` we enable [OS Login] which is required for using 
external SSH clients such as [PUTTY], etc. 

## Log into instance:

1. [Log to VM via gcloud]:

```
gcloud compute ssh --zone us-central1-a INSTANCE 
```

## Log to VM via putty:

[Log to VM via putty] (or other external ssh tool) requires more steps to get access:

1. [Grant access role] for non admin user: 

```
gcloud compute instances add-iam-policy-binding INSTANCE --zone us-central1-a \ 
    --member="user:EMAIL" \ 
    --role="roles/compute.osLogin"
``` 

2. Alternatively [grant access role] for admin user:

```
gcloud compute instances add-iam-policy-binding INSTANCE --zone us-central1-a \ 
    --member="user:EMAIL" \ 
    --role="roles/compute.osAdminLogin"
```

Read more how to properly [configure OS Login roles on user account].

3. [Create new ssh key] and save keys.

Notice that if you granted access to `EMAIL`, GCP replaces special characters into underscores, for example:

```
EMAIL = some.email@gmail.com

# gcp replaces email into:
GCP_USER = some_email_gmail_com
```

When you generate key, `key comment` must have `some_email_gmail_com` value !!!

4. [Import key] into VM by [OS Login]. Details are [here](https://cloud.google.com/compute/docs/instances/managing-instance-access#add_oslogin_keys)

```
gcloud compute os-login ssh-keys add --key-file PUBLIC_KEY --ttl 30d
```

4. Connect to vm by [PUTTY]

If you have problem with connection, [check trusted keys]:

```
gcloud compute os-login describe-profile
```

Look at:


```console
name: 'someName'
posixAccounts:
- accountId: some-account-id
  ...
  username: some_email_gmail_com
sshPublicKeys:
  ...
```

Did you create key for `some_email_gmail_com` ?

## Delete vm:

1. [Delete vm]:

```
gcloud -q compute instances delete INSTANCE
```

[machine types]: https://cloud.google.com/compute/docs/machine-types
[Log to VM via gcloud]: https://cloud.google.com/sdk/gcloud/reference/compute/ssh
[Log to VM via putty]: https://cloud.google.com/compute/docs/instances/connecting-advanced#thirdpartytools
[Create new ssh key]: https://cloud.google.com/compute/docs/instances/adding-removing-ssh-keys#createsshkeys
[Import key]: https://cloud.google.com/compute/docs/instances/connecting-advanced#provide-key
[OS Login]: https://cloud.google.com/compute/docs/oslogin
[Configure OS Login roles on user account]: https://cloud.google.com/compute/docs/instances/managing-instance-access#configure_users
[PUTTY]: https://www.putty.org/
[Grant access role]: https://cloud.google.com/sdk/gcloud/reference/compute/instances/add-iam-policy-binding
[Delete vm]: https://cloud.google.com/sdk/gcloud/reference/compute/instances/delete
[check trusted keys]: https://cloud.google.com/sdk/gcloud/reference/compute/os-login/describe-profile