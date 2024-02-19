# Schablonen

## Writer

Schreibende Methoden können mit oder ohne `wait()` ausgestattet sein, und/oder mit und ohne `notify()` / `notifyAll()` (Oec22, S. 139)

### Schablone wait / notify()
#### wait() / notify()

  1. SynchStack `pop()`
  2. DeutscheAmpel `passieren()`

#### wait() / notifyAll()

 1. BoundedCounter `up()` / `down()`


### Schablone  notify()
#### - / notify()

 1. SynchStack `push()`
 2. DeutscheAmpel `schalteGruen()()`

#### - / notifyAll()

1. Ampel `schalteRot()()`
2. ItalienischeAmpel `schalteGruen()()`
3. EventSet `set()`

### Schablone wait 
#### wait() / -

 1. ItalienischeAmpel `passieren()`
2. EventSet `waitOR()`
3. EventSet `waitAnd()`

### Schablone -
#### - / -

## Reader

Lesende Methoden können mit oder ohne `wait()` ausgestattet sein (Oec22, S. 139)

### Schablone wait 


#### wait() 

#### -

 1. BoundedCounter `get()`
2. Ampel `wartendeFahrzeuge()`