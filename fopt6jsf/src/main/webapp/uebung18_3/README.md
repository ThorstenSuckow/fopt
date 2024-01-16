# Remarks

increment() / reset() are now void, requiring a third method to access the counter-value.
In between increment()/getValue() and reset()/getValue() other clients may increment()/reset(), updating the value with an unexpected result.