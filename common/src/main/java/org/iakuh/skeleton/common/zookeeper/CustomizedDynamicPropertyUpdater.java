package org.iakuh.skeleton.common.zookeeper;

import com.netflix.config.DynamicPropertyUpdater;
import com.netflix.config.WatchedUpdateResult;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.configuration.Configuration;

public class CustomizedDynamicPropertyUpdater extends DynamicPropertyUpdater {

  private ZookeeperGroup zookeeperGroup;

  public CustomizedDynamicPropertyUpdater(ZookeeperGroup zookeeperGroup) {
    this.zookeeperGroup = zookeeperGroup;
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
        zookeeperGroup.put(entry.getKey(), entry.getValue());
      }
      if (!ignoreDeletesFromSource) {
        Set<String> existingKeys = new HashSet<>();
        existingKeys.addAll(zookeeperGroup.keySet());
        for (String key : existingKeys) {
          if (!complete.containsKey(key)) {
            zookeeperGroup.remove(key);
          }
        }
      }
    } else {
      Map<String, Object> added = result.getAdded();
      if (added != null) {
        for (Map.Entry<String, Object> entry : added.entrySet()) {
          zookeeperGroup.put(entry.getKey(), entry.getValue());
        }
      }
      Map<String, Object> changed = result.getChanged();
      if (changed != null) {
        for (Map.Entry<String, Object> entry : changed.entrySet()) {
          zookeeperGroup.put(entry.getKey(), entry.getValue());
        }
      }
      if (!ignoreDeletesFromSource) {
        Map<String, Object> deleted = result.getDeleted();
        if (deleted != null) {
          for (String name : deleted.keySet()) {
            zookeeperGroup.remove(name);
          }
        }
      }
    }
  }
}
