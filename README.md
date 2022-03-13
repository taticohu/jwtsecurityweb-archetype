# jwtsecurityweb-archetype

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

This is a JWT security springboot project.

## Table of Contents

- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Install

``` shell
$ mvn clean archetype:create-from-project
$ cd target/generated-sources/archetype
$ mvn install
```

## Usage

```
$ mvn archetype:generate -DarchetypeGroupId=indi.huhy.archetypes -DarchetypeArtifactId=jwtsecurityweb-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=com.example -DartifactId=demo -Dversion=1.0.0 -DarchetypeCatalog=local -DinteractiveMode=false -X
```

## Maintainers

[@taticohu](https://github.com/taticohu)

## Contributing



Small note: If editing the README, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

MIT © 2022 taticohu
