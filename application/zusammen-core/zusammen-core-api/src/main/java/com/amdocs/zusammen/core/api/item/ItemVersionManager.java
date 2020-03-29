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

package com.amdocs.zusammen.core.api.item;


import com.amdocs.zusammen.core.api.types.CoreItemVersionConflict;
import com.amdocs.zusammen.core.api.types.CoreMergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ItemVersion;
import com.amdocs.zusammen.datatypes.item.ItemVersionChange;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.ItemVersionStatus;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.datatypes.itemversion.Revision;
import com.amdocs.zusammen.datatypes.itemversion.Tag;

import java.util.Collection;
import java.util.Date;

public interface ItemVersionManager {

  Collection<ItemVersion> list(SessionContext context, Space space, Id itemId);

  boolean isExist(SessionContext context, Space space, Id itemId, Id versionId);

  ItemVersion get(SessionContext context, Space space, Id itemId, Id versionId, Id revisionId);

  Id create(SessionContext context, Id itemId, Id baseVersionId, ItemVersionData data);

  Id create(SessionContext context, Id itemId, Id versionId, Id baseVersionId, ItemVersionData data);

  void update(SessionContext context, Id itemId, Id versionId, ItemVersionData data);

  void delete(SessionContext context, Id itemId, Id versionId);

  ItemVersionStatus getStatus(SessionContext context, Id itemId, Id versionId);

  void tag(SessionContext context, Id itemId, Id versionId, Id changeId, Tag tag);

  void publish(SessionContext context, Id itemId, Id versionId, String message);

  CoreMergeResult sync(SessionContext context, Id itemId, Id versionId);

  CoreMergeResult forceSync(SessionContext context, Id itemId, Id versionId);

  CoreMergeResult merge(SessionContext context, Id itemId, Id versionId, Id sourceVersionId);

  ItemVersionRevisions listRevisions(SessionContext context, Id itemId, Id versionId);

  Revision getRevision(SessionContext context, Id itemId, Id versionId, Id revisionId);

  void resetRevision(SessionContext context, Id itemId, Id versionId, Id revisionId);

  void revertRevision(SessionContext context, Id itemId, Id versionId, Id revisionId);

  void updateModificationTime(SessionContext context, Space space, Id itemId, Id versionId,
                              Date modificationTime);

  CoreItemVersionConflict getConflict(SessionContext context, Id itemId, Id versionId);

  void saveMergeChange(SessionContext context, Space space, Id itemId,
                       ItemVersionChange itemVersionChange);
}
