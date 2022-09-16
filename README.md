## External Vault Server
To install vault server in docker for development use the following command:

```shell
$ docker run --cap-add=IPC_LOCK --name vault -p '1234:1234' -e 'VAULT_DEV_ROOT_TOKEN_ID=00000000-0000-0000-0000-000000000000' -e 'VAULT_DEV_LISTEN_ADDRESS=0.0.0.0:1234' vault
```

You'll also need the vault CLI. Installation instructions are [here](https://learn.hashicorp.com/tutorials/vault/getting-started-install).

To work properly with the vault server you'll need these env variables:
```shell
$ export VAULT_ADDR=http://127.0.0.1:1234
$ export VAULT_TOKEN=00000000-0000-0000-0000-000000000000
```

Put secrets into the vault:
```shell
$ vault kv put secret/application/creds url=jdbc:postgresql://localhost/tm1 username=postgres password=postgres
```
Please note that you'll need to append `data` after `secret` in configuration path. 
So, the application should query `secret/data/application/creds` path.

## Docker Compose
First time startup sequence:
- postgres-1
- postgres-2
- postgres
- vault

After starting vault, we need to put credentials into the storage. 
Please note that the storage is non-persistent, so data may be lost after restart. 
To add data, we need the vault client and env variables set as described in the previous section.
Config data for compose:
```shell
$ vault kv put secret/application/creds url=jdbc:postgresql://vault/tm username=postgres password=postgres
```

After that, we can start
- web1
- web2
- nginx

## Kubernetes

Configuration uses example from the [documentation](https://docs.jmix.io/jmix/deployment/k8s.html)

