Source code taken from [Oec22].

# Remarks

Client accesses Student-class, sets data, another client accesses the same @ApplicationScoped Student object, sets data. While communication with the server from Client A is rather slow, Client B already has send its data to the server, updating the Student-object, which will become the object Client A receives, not seeing the data originally submitted. 