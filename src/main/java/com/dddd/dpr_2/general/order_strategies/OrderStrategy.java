package com.dddd.dpr_2.general.order_strategies;

import com.dddd.dpr_2.database.models.OrderedModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStrategy<T extends OrderedModel, R extends JpaRepository<T, Long>> {

	void setOrder(T object, R repository, int order);

}
