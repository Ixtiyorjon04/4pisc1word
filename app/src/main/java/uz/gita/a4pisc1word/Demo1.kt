package uz.gita.a4pisc1word

public class Demo1 {
    class Main{


    fun main(args: Array<String>) {
        val a = A()
        a.show()
    }
}

internal class A {
    private val b: B
    fun show() {
        b.showMessage()
    }

    init {
        b = B()
        b.setListener(
            object : Listener {
                override fun onMessage(message: String?) {
                    System.out.printf(
                        message
                    )
                }
            }
        )
    }
}

internal class B {
    private var listener: Listener? = null
    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    fun showMessage() {
        listener!!.onMessage("Hello")
    }
}

@FunctionalInterface
interface Listener {
    fun onMessage(message: String?)
}}