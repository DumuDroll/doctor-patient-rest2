package com.dddd.dpr_2.general.order_strategies.strategies.chain_order_strategy;

import com.dddd.dpr_2.database.models.OrderedModel;
import com.dddd.dpr_2.general.order_strategies.OrderStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ChainOrderStrategy<T extends OrderedModel, R extends JpaRepository<T, Long>>
		implements OrderStrategy<T, R> {

	private ChainOrderStrategy<T, R> next;

	public ChainOrderStrategy<T, R> linkWith(ChainOrderStrategy<T, R> next){
		this.next = next;
		return next;
	}

	@Override
	public void setOrder(T object, R repository, int order) {
		MaxOrder<T, R> maxOrder = new MaxOrder<>();
		maxOrder.setOrder(object, repository, order);
	}

	protected void setNextOrder(T dto, R repository, int order){
		if(next==null){
			return;
		}
		next.setOrder(dto, repository, order);
	}
}
