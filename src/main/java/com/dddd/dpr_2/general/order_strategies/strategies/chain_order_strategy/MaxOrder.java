package com.dddd.dpr_2.general.order_strategies.strategies.chain_order_strategy;

import com.dddd.dpr_2.database.models.OrderedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaxOrder<T extends OrderedModel, R extends JpaRepository<T, Long>> extends ChainOrderStrategy<T, R> {


	@Override
	public void setOrder(T object, R repository, int order) {
		List<T> tList = repository.findAll();
		for (T obj : tList) {
			if (obj.getItemOrder() > order) {
				order = obj.getItemOrder();
			}
		}
		object.setItemOrder(order);

		setNextOrder(object, repository, order);
	}
}
