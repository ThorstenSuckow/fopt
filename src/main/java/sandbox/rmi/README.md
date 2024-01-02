## rmi

### Usage 

`RMIIncrementorServer` starts the Registry. If the registry should start as an external process, uncomment the appropriate lines in `RMIIncrementorServer` and start the `rmiregistry` with 

```bash
$ rmiregistry -J-Djava.class.path=[DIR]
```