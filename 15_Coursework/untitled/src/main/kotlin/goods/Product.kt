package goods

open class Product(
    private val name: String,
    val typeProduct: TypeProduct,
    val timeLoad: Long,
    val weight: Int
) {
}