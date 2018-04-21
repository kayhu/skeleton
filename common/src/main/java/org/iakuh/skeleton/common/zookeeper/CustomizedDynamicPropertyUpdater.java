package org.iakuh.skeleton.common.zookeeper;

import com.netflix.config.DynamicPropertyUpdater;
import com.netflix.config.WatchedUpdateResult;
import java.util.Map;
import org.apache.commons.configuration.Configuration;

public class CustomizedDynamicPropertyUpdater extends DynamicPropertyUpdater {

  private ZookeeperGroup zookeeperGroup;

  public CustomizedDynamicPropertyUpdater(ZookeeperGroup zookeeperGroup) {
    this.zookeeperGroup = zookeeperGroup;
  }

  @Override
  public void updateProperties(WatchedUpdateResult result, Configuration config,
      boolean ignoreDeletesFromSource) {
    this.updateZookeeperGroup(result, ignoreDeletesFromSource);
    super.updateProperties(result, config, ignoreDeletesFromSource);
  }

  private void updateZookeeperGroup(WatchedUpdateResult result, boolean ignoreDeletesFromSource) {
    if (result == null || !result.hasChanges()) {
      return;
    }

    if (!result.isIncremental()) {
      Map<String, Object> complete = result.getComplete();
      addOrUpdate(complete);

      if (complete != null && !ignoreDeletesFromSource) {
        zookeeperGroup.keySet().stream()
            .filter(key -> !complete.containsKey(key))
            .forEach(this::delete);
      }
    } else {
      Map<String, Object> added = result.getAdded();
      addOrUpdate(added);

      Map<String, Object> changed = result.getChanged();
      addOrUpdate(changed);

      if (!ignoreDeletesFromSource && result.getDeleted() != null) {
        result.getDeleted().keySet().forEach(this::delete);
      }
    }
  }

  private void addOrUpdate(Map<String, Object> mapToUpdate) {
    if (mapToUpdate != null) {
      zookeeperGroup.putAll(mapToUpdate);
    }
  }

  private void delete(String key) {
    zookeeperGroup.remove(key);
  }
}
