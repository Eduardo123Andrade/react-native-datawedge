import { useState, useEffect } from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { createProfile } from 'react-native-datawedge';

createProfile('DATAWEDGE', 'br.com.teste.teste.SCANNER', true);

export default function App() {
  // const [result, setResult] = useState<number | undefined>();
  const [temp, setTemp] = useState('Olar');

  useEffect(() => {
    // multiply(3, 7).then(setResult);
  }, []);

  return (
    <View style={styles.container}>
      {/* <Text>Result: {result}</Text> */}
      <Text>{temp}</Text>
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
