package rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.parameter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParameterValuesCurrencyPrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;


@Repository
public interface ParametersValueCurrencyPriceRepository extends JpaRepository<ParameterValuesCurrencyPrice, Long>{

}
