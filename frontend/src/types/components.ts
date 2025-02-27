export interface InputFieldProps {
  label: string;
  type: string;
  placeholder: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  children?: React.ReactNode;
  helperText?: string;
}

export interface PrimaryButtonProps {
  text: string;
  onClick?: () => void;
  disabled?: boolean;
}
