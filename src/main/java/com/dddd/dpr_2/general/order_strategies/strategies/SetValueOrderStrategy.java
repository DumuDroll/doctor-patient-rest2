package com.dddd.dpr_2.general.order_strategies.strategies;

import com.dddd.dpr_2.database.models.OrderedModel;
import com.dddd.dpr_2.general.order_strategies.OrderStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class SetValueOrderStrategy<T extends OrderedModel, R extends JpaRepository<T, Long>>
		implements OrderStrategy<T, R>{

	@Override
	public void setOrder(T object, R repository, int order) {
		object.setItemOrder(order);
	}
}