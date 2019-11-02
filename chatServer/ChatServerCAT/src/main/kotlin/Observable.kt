// Observable is implemented by Chathistory
interface Observable {
    fun registerObserver(observer: Observer)
    fun deRegisterObserver(observer: Observer)
}