package deps.practice.myapplication.models
open class CustomPojo (var name: String) {
    init {
        println("Customer initialized with value ${name}")
    }
    override fun toString() = "$name"
}