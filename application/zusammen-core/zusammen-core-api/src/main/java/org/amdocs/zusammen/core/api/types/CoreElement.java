/*
 * Copyright © 2016 European Support Limited
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

package org.amdocs.zusammen.core.api.types;

import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.item.ElementAction;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Relation;
import org.amdocs.zusammen.utils.fileutils.FileUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CoreElement {
  private ElementAction action = ElementAction.IGNORE;
  private Id id;
  private Id parentId;
  private Info info;
  private Collection<Relation> relations = Collections.EMPTY_LIST;
  private byte[] data;
  private byte[] searchableData;
  private byte[] visualization;
  private Collection<CoreElement> subElements = Collections.EMPTY_LIST;

  public ElementAction getAction() {
    return action;
  }

  public void setAction(ElementAction action) {
    this.action = action;
  }

  public Id getId() {
    return id;
  }

  public void setId(Id elementId) {
    id = elementId;
  }

  public Id getParentId() {
    return parentId;
  }

  public void setParentId(Id parentId) {
    this.parentId = parentId;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public Collection<Relation> getRelations() {
    return relations;
  }

  public void setRelations(Collection<Relation> relations) {
    this.relations = relations;
  }

  public InputStream getData() {
    return FileUtils.toInputStream(data);
  }

  public void setData(InputStream data) {
    this.data = FileUtils.toByteArray(data);
  }

  public InputStream getSearchableData() {
    if (Objects.isNull(searchableData)) {
      return null;
    } else {
      return FileUtils.toInputStream(searchableData);
    }
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = FileUtils.toByteArray(searchableData);
  }

  public InputStream getVisualization() {
    return FileUtils.toInputStream(visualization);
  }

  public void setVisualization(InputStream visualization) {
    this.visualization = FileUtils.toByteArray(visualization);
  }

  public Collection<CoreElement> getSubElements() {
    return subElements;
  }

  public void setSubElements(Collection<CoreElement> subElements) {
    this.subElements = subElements;
  }
}
