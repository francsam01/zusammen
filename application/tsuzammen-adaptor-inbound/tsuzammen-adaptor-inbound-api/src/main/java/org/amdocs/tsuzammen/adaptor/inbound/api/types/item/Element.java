/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package org.amdocs.tsuzammen.adaptor.inbound.api.types.item;

import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.item.ElementAction;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.Relation;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchableData;

import java.io.InputStream;
import java.util.Collection;

public interface Element {

  ElementAction getAction();

  void setAction(ElementAction action);

  Id getElementId();

  void setElementId(Id elementId);

  Info getInfo();

  void setInfo(Info info);

  Collection<Relation> getRelations();

  void setRelations(Collection<Relation> relations);

  void setData(InputStream data);

  void setSearchableData(SearchableData searchableData);

  void setVisualization(InputStream visualization);

  InputStream getData();

  SearchableData getSearchableData();

  InputStream getVisualization();

  Collection<Element> getSubElements();

  void setSubElements(Collection<Element> subElements);
}
