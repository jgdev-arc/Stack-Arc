import org.springframework.data.jpa.repository.JpaRepository
import com.tlz.Stack_Arc.models.Product
import com.tlz.Stack_Arc.models.Supplier

interface SupplierRepository : JpaRepository<Supplier, Long> {

}
