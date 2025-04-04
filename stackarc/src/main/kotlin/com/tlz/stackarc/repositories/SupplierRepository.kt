import org.springframework.data.jpa.repository.JpaRepository
import com.tlz.stackarc.models.Product
import com.tlz.stackarc.models.Supplier

interface SupplierRepository : JpaRepository<com.tlz.stackarc.models.Supplier, Long> {

}
