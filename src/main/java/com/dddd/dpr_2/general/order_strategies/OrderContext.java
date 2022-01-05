package com.dddd.dpr_2.general.order_strategies;

import com.dddd.dpr_2.database.models.OrderedModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderContext<T extends OrderedModel, R extends JpaRepository<T, Long>> {

	private T object;

	private R repository;

	private int order;

	private OrderStrategy<T, R> orderStrategy;

	public void executeStrategy() {
		orderStrategy.setOrder(object, repository, order);
	}
}
