/*
 * Copyright © 2016-2017 European Support Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amdocs.zusammen.adaptor.inbound.impl.item;

import com.amdocs.zusammen.adaptor.inbound.api.item.ItemAdaptor;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.item.ItemManager;
import com.amdocs.zusammen.core.api.item.ItemManagerFactory;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.Item;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;

public class ItemAdaptorImpl implements ItemAdaptor {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ItemAdaptorImpl.class
      .getName());

  @Override
  public Response<Collection<Item>> list(SessionContext context) {
    Response<Collection<Item>> response;
    try {
      Collection<Item> itemCollection = getItemManager(context).list(context);
      response = new Response<>(itemCollection);
    } catch (ZusammenException ze) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_LIST, Module.ZDB,null,ze
          .getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Item> get(SessionContext context, Id itemId) {
    Response<Item> response;
    try {
      Item item = getItemManager(context).get(context, itemId);
      response = new Response<>(item);
    } catch (ZusammenException ze) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_GET, Module.ZDB,null,ze
          .getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Id> create(SessionContext context, Info itemInfo) {

    Response<Id> response;
    try {
      Id id = getItemManager(context).create(context, itemInfo);
      response = new Response<>(id);
      logger.info("create item:"+id.getValue());
    } catch (ZusammenException ze) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_CREATE, Module.ZDB,null,ze
          .getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }

    return response;

  }

  @Override
  public Response<Id> create(SessionContext context, Id itemId, Info itemInfo) {
    Response<Id> response;
    try {
      Id id = getItemManager(context).create(context, itemId, itemInfo);
      response = new Response<>(id);
      logger.info("create item:" + id.getValue());
    } catch (ZusammenException ze) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_CREATE, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }

    return response;
  }

  @Override
  public Response<Void> update(SessionContext context, Id itemId, Info itemInfo) {
    Response response;
    try {
      getItemManager(context).update(context, itemId, itemInfo);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_UPDATE, Module.ZDB,null,ze
          .getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Void> delete(SessionContext context, Id itemId) {
    Response response;
    try {
      getItemManager(context).delete(context, itemId);
      response = new Response<>(Void.TYPE);
    } catch (ZusammenException ze) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_DELETE, Module.ZDB,null,ze
          .getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  private ItemManager getItemManager(SessionContext context) {
    return ItemManagerFactory.getInstance().createInterface(context);
  }
}
