import { useEffect, useState } from 'react';
import { DeviceEventEmitter } from 'react-native';
import { onInit } from 'react-native-datawedge';

const DEFAULT_CONFIG: Omit<ScannerConfig, 'id'> = {
  canReset: true,
  canScan: true,
  timeOutToReset: 500,
};

export const useScanner = () => {
  const [scanner, setScanner] = useState<string>();
  const [config, _setConfig] = useState<ScannerConfig>();

  const onScanner = (data: string) => {
    setScanner(data);
  };

  /**
   *
   * @param {ScannerConfig} config
   */
  const setConfig = (config: ScannerConfig) => {
    _setConfig({ ...DEFAULT_CONFIG, ...config });
  };

  useEffect(() => {
    if (scanner && config && config.canScan && config.onCallbackScanner) {
      config.onCallbackScanner(scanner);
    }
    if (scanner && config && config.canScan && config.canReset)
      setTimeout(() => setScanner(undefined), config.timeOutToReset);
  }, [scanner, config]);

  useEffect(() => {
    if (!config || !config?.id) return;

    const eventId = config.id;
    if (!config.canScan) {
      DeviceEventEmitter.removeAllListeners(`onScanner-${eventId}`);
      return;
    }

    onInit(eventId);
    DeviceEventEmitter.addListener(`onScanner-${eventId}`, onScanner);
  }, [config]);

  return { scanner, setConfig };
};
