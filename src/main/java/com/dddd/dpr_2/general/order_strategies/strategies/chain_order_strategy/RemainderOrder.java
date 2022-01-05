package com.dddd.dpr_2.general.order_strategies.strategies.chain_order_strategy;

import com.dddd.dpr_2.database.models.OrderedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RemainderOrder<T extends OrderedModel, R extends JpaRepository<T, Long>> extends ChainOrderStrategy<T, R> {

	@Override
	public void setOrder(T object, R repository, int order) {
		if(object.getItemOrder()%10==0){
			object.setItemOrder(object.getItemOrder()+1);
		}

		setNextOrder(object, repository, order);
	}
}
