import com.tlz.Stack_Arc.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import com.tlz.Stack_Arc.models.Product

interface CategoryRepository : JpaRepository<Category, Long> {

}
