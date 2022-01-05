package com.dddd.dpr_2.general.config;

import com.dddd.dpr_2.database.models.OrderedModel;
import com.dddd.dpr_2.general.order_strategies.strategies.chain_order_strategy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class ChainStrategyConfig<T extends OrderedModel, R extends JpaRepository<T, Long>> {

	private final MaxOrder<T, R> maxOrder;

	private final RandOrder<T, R> randOrder;

	private final IncrementOrder<T, R> incrementOrder;

	private final RemainderOrder<T, R> remainderOrder;

	public ChainStrategyConfig(MaxOrder<T, R> maxOrder,
							   RandOrder<T, R> randOrder,
							   IncrementOrder<T, R> incrementOrder,
							   RemainderOrder<T, R> remainderOrder) {
		this.maxOrder = maxOrder;
		this.randOrder = randOrder;
		this.incrementOrder = incrementOrder;
		this.remainderOrder = remainderOrder;
	}

	@Bean
	public ChainOrderStrategy<T, R> chainOrderStrategy() {
		ChainOrderStrategy<T, R> strategy = maxOrder;
		strategy.linkWith(randOrder)
				.linkWith(incrementOrder)
				.linkWith(remainderOrder);
		return strategy;
	}

}
