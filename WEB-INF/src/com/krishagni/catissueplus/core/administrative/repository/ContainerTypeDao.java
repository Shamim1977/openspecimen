package com.krishagni.catissueplus.core.administrative.repository;

import java.util.Collection;
import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.ContainerType;
import com.krishagni.catissueplus.core.common.repository.Dao;

public interface ContainerTypeDao extends Dao<ContainerType> {
	public List<ContainerType> getTypes(ContainerTypeListCriteria crit);

	public Long getTypesCount(ContainerTypeListCriteria crit);

	public List<ContainerType> getByNames(Collection<String> names);

	public ContainerType getByName(String name);
}
