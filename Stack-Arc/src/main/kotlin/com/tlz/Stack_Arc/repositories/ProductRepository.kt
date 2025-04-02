import org.springframework.data.jpa.repository.JpaRepository
import com.tlz.Stack_Arc.models.Product

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByNameContainingOrDescriptionContaining(
        name: String,
        description: String
    ): List<Product>
}
