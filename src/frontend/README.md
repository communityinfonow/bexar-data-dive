# cinow-ui

## Project setup

```
yarn install
```

### Compiles and hot-reloads for development

```
yarn serve
```

### Compiles and minifies for production

```
yarn build
```

### Run your unit tests

```
yarn test:unit
```

### Run your end-to-end tests

```
yarn test:e2e
```

### Lints and fixes files

```
yarn lint
```

### Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).


sudo docker create --name omh-local -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASS=example -e POSTGRES_DBNAME=omh_local -t kartoza/postgis
sudo docker start omh-local
sudo docker stop omh-local