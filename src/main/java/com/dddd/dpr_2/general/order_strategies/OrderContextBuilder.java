package com.dddd.dpr_2.general.order_strategies;

import com.dddd.dpr_2.database.models.OrderedModel;
import org.springframework.data.jpa.repository.JpaRepository;

public class OrderContextBuilder<T extends OrderedModel, R extends JpaRepository<T, Long>> {

	private T object;

	private R repository;

	private int order = 0;

	private OrderStrategy<T, R> orderStrategy;

	public OrderContextBuilder<T, R> setObject(T object) {
		this.object = object;
		return this;
	}

	public OrderContextBuilder<T, R> setRepository(R repository) {
		this.repository = repository;
		return this;
	}

	public OrderContextBuilder<T, R> setOrder(int order) {
		this.order = order;
		return this;
	}

	public OrderContextBuilder<T, R> setOrderStrategy(OrderStrategy<T, R> orderStrategy) {
		this.orderStrategy = orderStrategy;
		return this;
	}


	public OrderContext<T, R> build() {
		return new OrderContext<>(object, repository, order, orderStrategy);
	}

}
