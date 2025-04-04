import org.springframework.data.jpa.repository.JpaRepository
import com.tlz.stackarc.models.Product

interface ProductRepository : JpaRepository<com.tlz.stackarc.models.Product, Long> {
    fun findByNameContainingOrDescriptionContaining(
        name: String,
        description: String
    ): List<com.tlz.stackarc.models.Product>
}
