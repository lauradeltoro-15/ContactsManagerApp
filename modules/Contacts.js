import { NativeModules } from 'react-native';
const { ContactsModule } = NativeModules;


export function createContact() {
    ContactsModule.createContact("Pepito", "58937219281")
}