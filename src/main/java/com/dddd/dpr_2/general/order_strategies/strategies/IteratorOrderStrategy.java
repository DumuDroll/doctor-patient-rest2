package com.dddd.dpr_2.general.order_strategies.strategies;

import com.dddd.dpr_2.database.models.OrderedModel;
import com.dddd.dpr_2.general.order_strategies.OrderStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IteratorOrderStrategy<T extends OrderedModel, R extends JpaRepository<T, Long>>
		implements OrderStrategy<T, R> {

	@Override
	public void setOrder(T object, R repository, int order) {
		List<T> tList = repository.findAll();
		for(T obj: tList){
			obj.setItemOrder(obj.getItemOrder()+1);
			repository.save(obj);
		}
	}
}
