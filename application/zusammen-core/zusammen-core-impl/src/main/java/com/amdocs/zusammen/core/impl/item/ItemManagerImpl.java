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

package com.amdocs.zusammen.core.impl.item;


import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import com.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.item.ItemManager;
import com.amdocs.zusammen.core.impl.Messages;
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
import java.util.Date;

public class ItemManagerImpl implements ItemManager {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ItemManagerImpl.class
      .getName());

  @Override
  public Collection<Item> list(SessionContext context) {
    Response<Collection<Item>> response;

    response = getStateAdaptor(context).listItems(context);
    if (!response.isSuccessful()) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_LIST, Module.ZDB, null,
              response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    return response.getValue();
  }

  @Override
  public boolean isExist(SessionContext context, Id itemId) {
    Response<Boolean> response;
    response = getStateAdaptor(context).isItemExist(context, itemId);
    if (!response.isSuccessful()) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_IS_EXIST, Module.ZDB, null,
              response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    return response.getValue();
  }

  @Override
  public Item get(SessionContext context, Id itemId) {
    Response<Item> response;
    response = getStateAdaptor(context).getItem(context, itemId);
    if (!response.isSuccessful()) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_GET, Module.ZDB, null, response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response.getValue();
  }

  @Override
  public Id create(SessionContext context, Info itemInfo) {
    return createItem(context, new Id(), itemInfo);
  }

  @Override
  public Id create(SessionContext context, Id itemId, Info itemInfo) {
    if (itemId == null || itemId.getValue() == null) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.ZU_ITEM_CREATE, Module.ZDB, Messages.ITEM_ID_TO_CREATE_CANNOT_BE_NULL, null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (isExist(context, itemId)) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_CREATE, Module.ZDB,
              String.format(Messages.ITEM_ֹID_ALREADY_EXIST, itemId), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return createItem(context, itemId, itemInfo);
  }

  private Id createItem(SessionContext context, Id itemId, Info itemInfo) {
    Date creationTime = new Date();
    Response response;
    response = getCollaborationAdaptor(context).createItem(context, itemId, itemInfo);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_CREATE, Module.ZDB, null, response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    response = getStateAdaptor(context).createItem(context, itemId, itemInfo, creationTime);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_CREATE, Module.ZDB, null, response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return itemId;
  }

  @Override
  public void update(SessionContext context, Id itemId, Info itemInfo) {
    validateItemExistence(context, itemId);
    Response response;
    response = getCollaborationAdaptor(context).updateItem(context, itemId, itemInfo);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_UPDATE, Module.ZDB, null, response
          .getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    Date modificationTime = new Date();
    response = getStateAdaptor(context).updateItem(context, itemId, itemInfo,modificationTime);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_UPDATE, Module.ZDB, null, response
          .getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public void delete(SessionContext context, Id itemId) {
    validateItemExistence(context, itemId);
    Response response;
    response = getCollaborationAdaptor(context).deleteItem(context, itemId);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_DELETE, Module.ZDB, null, response
          .getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    response = getStateAdaptor(context).deleteItem(context, itemId);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_DELETE, Module.ZDB, null, response
          .getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public void updateModificationTime(SessionContext context, Id itemId, Date modificationTime) {
    getStateAdaptor(context).updateItemModificationTime(context,itemId,modificationTime);
  }

  private void validateItemExistence(SessionContext context, Id itemId) {
    if (!isExist(context, itemId)) {

      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_DOES_NOT_EXIST, Module.ZDB, String
          .format(Messages.ITEM_NOT_EXIST,
              itemId), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}
