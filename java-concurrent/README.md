# Java 并发

<https://www.bilibili.com/video/BV16J411h7Rd?p=21>

Java中的并发是对线程的控制，Java中的线程执行是`Thread`,`Thread`提供了对线程执行的控制。例如`start`,`sleep`,`run`等。  
而具体的逻辑执行，需要提供一个`执行体`。例如`Runnable`和`FutureTask`。

- `Runnable` 无返回，无法获取执行结果。   
- `FutureTask` 是对`Runnable`的增强，提供了对执行体的控制，例如获取执行结果

`FutureTask` 是对执行体的控制，其执行逻辑需要通过一个`Callable`提供，其提供一个`call`方法，他会提供执行逻辑的结果返回