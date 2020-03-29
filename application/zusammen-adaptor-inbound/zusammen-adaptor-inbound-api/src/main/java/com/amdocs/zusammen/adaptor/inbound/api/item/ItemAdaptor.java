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

package com.amdocs.zusammen.adaptor.inbound.api.item;


import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.Item;

import java.util.Collection;

public interface ItemAdaptor {

  Response<Collection<Item>> list(SessionContext context);

  Response<Item> get(SessionContext context, Id itemId);

  Response<Id> create(SessionContext context, Info itemInfo);

  Response<Id> create(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> update(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> delete(SessionContext context, Id itemId);
}
