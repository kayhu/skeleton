package org.iakuh.skeleton.common.zookeeper;

import com.netflix.config.DynamicPropertyUpdater;
import com.netflix.config.WatchedUpdateResult;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.configuration.Configuration;

public class CustomizedDynamicPropertyUpdater extends DynamicPropertyUpdater {

  private ZooKeeperGroup zooKeeperGroup;

  public CustomizedDynamicPropertyUpdater(ZooKeeperGroup zooKeeperGroup) {
    this.zooKeeperGroup = zooKeeperGroup;
  }

  @Override
  public void updateProperties(WatchedUpdateResult result, Configuration config,
      boolean ignoreDeletesFromSource) {
    this.updateZooKeeperGroup(result, config, ignoreDeletesFromSource);
    super.updateProperties(result, config, ignoreDeletesFromSource);
  }

  private void updateZooKeeperGroup(WatchedUpdateResult result, Configuration config,
      boolean ignoreDeletesFromSource) {
    if (result == null || !result.hasChanges()) {
      return;
    }

    if (!result.isIncremental()) {
      Map<String, Object> complete = result.getComplete();
      if (complete == null) {
        return;
      }
      for (Map.Entry<String, Object> entry : complete.entrySet()) {
        zooKeeperGroup.put(entry.getKey(), entry.getValue());
      }
      if (!ignoreDeletesFromSource) {
        Set<String> existingKeys = new HashSet<>();
        existingKeys.addAll(zooKeeperGroup.keySet());
        for (String key : existingKeys) {
          if (!complete.containsKey(key)) {
            zooKeeperGroup.remove(key);
          }
        }
      }
    } else {
      Map<String, Object> added = result.getAdded();
      if (added != null) {
        for (Map.Entry<String, Object> entry : added.entrySet()) {
          zooKeeperGroup.put(entry.getKey(), entry.getValue());
        }
      }
      Map<String, Object> changed = result.getChanged();
      if (changed != null) {
        for (Map.Entry<String, Object> entry : changed.entrySet()) {
          zooKeeperGroup.put(entry.getKey(), entry.getValue());
        }
      }
      if (!ignoreDeletesFromSource) {
        Map<String, Object> deleted = result.getDeleted();
        if (deleted != null) {
          for (String name : deleted.keySet()) {
            zooKeeperGroup.remove(name);
          }
        }
      }
    }
  }
}
