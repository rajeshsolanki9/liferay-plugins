/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.engine.service.persistence;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import com.liferay.sync.engine.model.SyncWatchEvent;

import java.sql.SQLException;

import java.util.List;

/**
 * @author Michael Young
 */
public class SyncWatchEventPersistence
	extends BasePersistenceImpl<SyncWatchEvent, Long> {

	public SyncWatchEventPersistence() throws SQLException {
		super(SyncWatchEvent.class);
	}

	public SyncWatchEvent fetchByF_K_T(
			String filePathName, String kindName, long timestamp)
		throws SQLException {

		QueryBuilder<SyncWatchEvent, Long> queryBuilder = queryBuilder();

		Where where = queryBuilder.where();

		where.eq("filePathName", filePathName);

		where.and();

		where.eq("kindName", kindName);

		where.and();

		where.ge("timestamp", timestamp - 1000);

		where.or();

		where.le("timestamp", timestamp + 1000);

		List<SyncWatchEvent> syncWatchEvents = query(queryBuilder.prepare());

		if ((syncWatchEvents == null) || syncWatchEvents.isEmpty()) {
			return null;
		}

		return syncWatchEvents.get(0);
	}

	public List<SyncWatchEvent> findAll(String orderByColumn, boolean ascending)
		throws SQLException {

		QueryBuilder<SyncWatchEvent, Long> queryBuilder = queryBuilder();

		queryBuilder.orderBy(orderByColumn, ascending);

		return query(queryBuilder.prepare());
	}

}