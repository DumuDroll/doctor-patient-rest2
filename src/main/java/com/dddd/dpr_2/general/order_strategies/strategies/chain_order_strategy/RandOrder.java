package com.dddd.dpr_2.general.order_strategies.strategies.chain_order_strategy;

import com.dddd.dpr_2.database.models.OrderedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandOrder<T extends OrderedModel, R extends JpaRepository<T, Long>> extends ChainOrderStrategy<T, R> {

	@Override
	public void setOrder(T object, R repository, int order) {
		object.setItemOrder(object.getItemOrder() + ThreadLocalRandom.current().nextInt(0, 8));

		setNextOrder(object, repository, order);
	}
}
