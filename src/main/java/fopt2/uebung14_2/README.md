## Übung 14.2
Die Verklemmung kann in diesem Beispiel verhindert werden, indem für alle Threads eine feste Reihenfolge
für den Zugriff auf die Bankkonten gewährleistet wird.

So könnte bspw. die Aufrufreihenfolge von 

```java

synchronized (account[fromAccountNumber]) {
    synchronized (account[toAccountNumber]) {
        
    }
    
}
```

so umgestellt werden, das zuerst die kleinere Accountnummer, danach die größere gesperrt wird:


```java

if (fromAccountNumber < toAccountNumber) {
    lowerNumber = fromAccountNumber;
    higherNumber = toAccountNumber;
} else {
    lowerNumber = toAccountNumber;
    higherNumber = fromAccountNumber;
}

synchronized (account[lowerNumber]) {
    synchronized (account[higherNumber]) {
        
    }
    
}
```

Threads, die mit den gleichen Kontodaten arbeiten, müssen damit in einer festen Reihenfolge die Sperren setzen - eine
Verklemmung ist ausgeschlossen (siehe [Oec22, S.193, Punkt 2]).

Denkbar wäre auch eine Semaphorgruppe, die entsprechend viele Felder enthält, die je nach Accountnummer
freigegeben oder gesperrt werden. In der Praxis wäre das aber bei einer hohen Zahl von Kontonummern aus 
Performancegründen wenig sinnvoll.