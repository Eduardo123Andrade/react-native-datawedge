import * as React from 'react';

import { StyleSheet, View, Text, Button } from 'react-native';
import { createProfile, useScanner } from 'react-native-datawedge';
import { ModalScanner } from './components';

createProfile('Zebra Scanner', 'br.com.example.zebra.SCANNER');

export default function App() {
  const [showModal1, setShowModal1] = React.useState(false);
  const [showModal2, setShowModal2] = React.useState(false);
  const [showModal3, setShowModal3] = React.useState(false);

  const { scanner, setConfig } = useScanner();

  const onRequestClose = () => {
    setShowModal1(false);
    setShowModal2(false);
    setShowModal3(false);
  };

  React.useEffect(() => {
    const canScan = !(showModal1 || showModal1 || showModal1);
    setConfig({ canScan, canReset: false, id: 'FIRST_SCAN' });
  }, [showModal1, showModal1, showModal1]);

  React.useEffect(() => {
    console.log({ scanner });
  }, [scanner]);

  return (
    <View style={styles.container}>
      <Text>Result: {scanner}</Text>

      <View
        style={{
          paddingTop: 20,
          gap: 20,
        }}
      >
        <Button title="Modal 1" onPress={() => setShowModal1(true)} />

        <Button title="Modal 2" onPress={() => setShowModal2(true)} />

        <Button title="Modal 3" onPress={() => setShowModal3(true)} />
      </View>

      <ModalScanner
        visible={showModal1}
        onRequestClose={onRequestClose}
        title="Modal 1"
        id="MODAL_1"
      />

      <ModalScanner
        visible={showModal2}
        onRequestClose={onRequestClose}
        id="MODAL_2"
        title="Modal 2"
      />

      <ModalScanner
        visible={showModal3}
        onRequestClose={onRequestClose}
        id="MODAL_3"
        title="Modal 3"
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
