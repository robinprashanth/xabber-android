/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * 
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * 
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package com.xabber.android.data.message.chat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.xabber.android.data.DatabaseManager;

/**
 * Storage with settings to notify about messages in visible chat.
 * 
 * @author alexander.ivanov
 * 
 */
class NotifyVisibleTable extends AbstractChatPropertyTable<Boolean> {

	static final String NAME = "chat_notify_visible";

	private final static NotifyVisibleTable instance;

	static {
		instance = new NotifyVisibleTable(DatabaseManager.getInstance());
		DatabaseManager.getInstance().addTable(instance);
	}

	public static NotifyVisibleTable getInstance() {
		return instance;
	}

	private NotifyVisibleTable(DatabaseManager databaseManager) {
		super(databaseManager);
	}

	@Override
	protected String getTableName() {
		return NAME;
	}

	@Override
	String getValueType() {
		return "INTEGER";
	}

	@Override
	void bindValue(SQLiteStatement writeStatement, Boolean value) {
		writeStatement.bindLong(3, value ? 1 : 0);
	}

	@Override
	public void migrate(SQLiteDatabase db, int toVersion) {
		super.migrate(db, toVersion);
		switch (toVersion) {
		case 52:
			initialMigrate(db, "chat_notify_visible", "INTEGER");
			break;
		default:
			break;
		}
	}

	static boolean getValue(Cursor cursor) {
		return cursor.getLong(cursor.getColumnIndex(Fields.VALUE)) != 0;
	}

}
